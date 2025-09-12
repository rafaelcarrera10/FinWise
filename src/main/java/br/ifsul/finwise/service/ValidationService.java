package br.ifsul.finwise.service;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Serviço para validação e autenticação de dados de entrada
 * Inclui validações de email, nome e filtros de conteúdo inadequado
 */
@Service
public class ValidationService {
    
    // Regex para validação de email
    private static final String EMAIL_REGEX = 
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    
    // Regex para validação de nome (apenas letras, espaços, acentos e hífens)
    private static final String NAME_REGEX = 
        "^[a-zA-ZÀ-ÿ\\s\\-']+$";
    
    // Regex para detectar palavras soltas sem nexo
    private static final String RANDOM_WORDS_REGEX = 
        "\\b(arroz|feijão|batata|cenoura|tomate|cebola|alho|sal|açúcar|pão|leite|água|carne|frango|peixe|ovo|queijo|manteiga|óleo|vinagre|limão|laranja|maçã|banana|uva|morango|abacaxi|manga|pera|kiwi|melancia|melão|abacate|goiaba|maracujá|açaí|coco|castanha|amendoim|noz|avelã|amêndoa|pistache|macadâmia|pinhão|aipim|mandioca|inhame|batata-doce|abóbora|chuchu|berinjela|pimentão|alface|rúcula|agrião|espinafre|couve|repolho|brócolis|couve-flor|beterraba|rabanete|nabo|cenoura|salsinha|cebolinha|coentro|manjericão|orégano|tomilho|alecrim|sálvia|louro|canela|cravo|noz-moscada|pimenta|cominho|coentro|gengibre|açafrão|curry|páprica|mostarda|ketchup|maionese|catchup|molho|tempero|condimento)\\b";
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
    private static final Pattern RANDOM_WORDS_PATTERN = Pattern.compile(RANDOM_WORDS_REGEX, Pattern.CASE_INSENSITIVE);
    
    // Lista de palavrões e palavras inadequadas (em português)
    private static final Set<String> PROFANITY_WORDS = new HashSet<>(Arrays.asList(
        // Palavrões comuns (mascarados para demonstração)
        "p****", "c****", "f****", "m****", "b****", "d****", "r****",
        "caralho", "porra", "merda", "bosta", "cacete", "puta", "puto",
        "foda", "foder", "fodido", "fodida", "fodendo", "fodeu",
        "merda", "merdoso", "merdosa", "merdão", "merdão",
        "bosta", "bostão", "bostosa", "bostoso",
        "cacete", "cacetada", "cacetão", "cacetudo", "cacetuda",
        "puta", "puto", "putaria", "putanheiro", "putanheira",
        "caralho", "caralhada", "caralhão", "caralhudo", "caralhuda",
        "desgraça", "desgraçado", "desgraçada", "desgração",
        "maldito", "maldita", "maldição", "maldizer",
        "diabo", "demonio", "satanás", "inferno",
        "idiota", "imbecil", "burro", "burra", "estúpido", "estúpida",
        "retardado", "retardada", "deficiente", "mongol", "mongoloide",
        "gay", "viado", "bicha", "sapatão", "sapatona", "traveco", "travesti",
        "prostituta", "puta", "garota de programa", "meretriz", "cortesã",
        "vagabundo", "vagabunda", "marginal", "bandido", "bandida",
        "ladrão", "ladra", "roubar", "furto", "assalto", "assaltante",
        "matar", "assassino", "assassina", "homicídio", "morte",
        "drogas", "maconha", "cocaína", "heroína", "crack", "ecstasy",
        "bebida", "álcool", "bêbado", "bêbada", "embriagado", "embriagada",
        "sexo", "sexual", "pornografia", "pornô", "nudez", "nude",
        "violência", "violento", "violenta", "agressão", "agressivo", "agressiva",
        "racismo", "racista", "preconceito", "discriminação", "xenofobia",
        "nazismo", "fascismo", "ditadura", "tirania", "opressão",
        "terrorismo", "terrorista", "bomba", "explosão", "atentado",
        "suicídio", "suicida", "matar-se", "morrer", "morte"
    ));
    
    // Lista de palavras que indicam spam ou conteúdo inadequado
    private static final Set<String> SPAM_WORDS = new HashSet<>(Arrays.asList(
        "spam", "lixo", "junk", "promoção", "oferta", "desconto", "grátis",
        "gratuito", "free", "cashback", "bônus", "prêmio", "sorteio",
        "loteria", "aposta", "jogo", "cassino", "poker", "blackjack",
        "bitcoin", "criptomoeda", "investimento", "renda", "lucro",
        "ganhar dinheiro", "trabalho em casa", "home office", "freelancer",
        "venda", "comprar", "vender", "negócio", "empresa", "corporação",
        "marketing", "publicidade", "anúncio", "propaganda", "comercial",
        "telemarketing", "call center", "atendimento", "suporte", "help desk",
        "contato", "ligar", "telefone", "whatsapp", "telegram", "skype",
        "email", "mensagem", "sms", "notificação", "alerta", "lembrete"
    ));
    
    /**
     * Valida se um email está em formato correto
     * @param email Email para validar
     * @return true se o email é válido, false caso contrário
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        email = email.trim().toLowerCase();
        
        // Verifica formato básico
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return false;
        }
        
        // Verifica se não contém caracteres suspeitos
        if (email.contains("..") || email.startsWith(".") || email.endsWith(".")) {
            return false;
        }
        
        // Verifica domínios suspeitos
        String[] suspiciousDomains = {
            "tempmail", "10minutemail", "guerrillamail", "mailinator",
            "throwaway", "temp", "fake", "spam", "junk", "trash"
        };
        
        for (String domain : suspiciousDomains) {
            if (email.contains(domain)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Valida se um nome está em formato correto e não contém conteúdo inadequado
     * @param name Nome para validar
     * @return true se o nome é válido, false caso contrário
     */
    public boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        
        name = name.trim();
        
        // Verifica se contém apenas caracteres permitidos
        if (!NAME_PATTERN.matcher(name).matches()) {
            return false;
        }
        
        // Verifica tamanho mínimo e máximo
        if (name.length() < 2 || name.length() > 100) {
            return false;
        }
        
        // Verifica se não contém palavrões
        if (containsProfanity(name)) {
            return false;
        }
        
        // Verifica se não contém palavras soltas sem nexo
        if (containsRandomWords(name)) {
            return false;
        }
        
        // Verifica se não contém spam
        if (containsSpam(name)) {
            return false;
        }
        
        // Verifica se não contém números ou caracteres especiais inadequados
        if (containsInappropriateCharacters(name)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Verifica se o texto contém palavrões
     * @param text Texto para verificar
     * @return true se contém palavrões, false caso contrário
     */
    public boolean containsProfanity(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        text = text.toLowerCase().trim();
        
        // Verifica cada palavra do texto
        String[] words = text.split("\\s+");
        for (String word : words) {
            // Remove pontuação para verificação
            word = word.replaceAll("[^a-zA-ZÀ-ÿ]", "");
            if (PROFANITY_WORDS.contains(word)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Verifica se o texto contém palavras soltas sem nexo
     * @param text Texto para verificar
     * @return true se contém palavras soltas, false caso contrário
     */
    public boolean containsRandomWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        return RANDOM_WORDS_PATTERN.matcher(text.toLowerCase()).find();
    }
    
    /**
     * Verifica se o texto contém spam
     * @param text Texto para verificar
     * @return true se contém spam, false caso contrário
     */
    public boolean containsSpam(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        text = text.toLowerCase().trim();
        
        // Verifica cada palavra do texto
        String[] words = text.split("\\s+");
        for (String word : words) {
            // Remove pontuação para verificação
            word = word.replaceAll("[^a-zA-ZÀ-ÿ]", "");
            if (SPAM_WORDS.contains(word)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Verifica se o texto contém caracteres inadequados
     * @param text Texto para verificar
     * @return true se contém caracteres inadequados, false caso contrário
     */
    public boolean containsInappropriateCharacters(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        // Verifica se contém números (exceto em nomes como "João 2" ou "Maria III")
        if (text.matches(".*\\d{2,}.*")) {
            return true;
        }
        
        // Verifica se contém caracteres especiais inadequados
        if (text.matches(".*[!@#$%^&*()_+=\\[\\]{}|;':\",./<>?].*")) {
            return true;
        }
        
        // Verifica se contém sequências repetitivas
        if (text.matches(".*(.)\\1{2,}.*")) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Valida dados completos de um usuário
     * @param name Nome do usuário
     * @param email Email do usuário
     * @return ValidationResult com resultado da validação
     */
    public ValidationResult validateUserData(String name, String email) {
        ValidationResult result = new ValidationResult();
        
        // Valida nome
        if (!isValidName(name)) {
            result.addError("Nome inválido: deve conter apenas letras, espaços, acentos e hífens. Não pode conter palavrões, palavras soltas ou caracteres inadequados.");
        }
        
        // Valida email
        if (!isValidEmail(email)) {
            result.addError("Email inválido: formato incorreto ou domínio suspeito.");
        }
        
        return result;
    }
    
    /**
     * Classe para resultado de validação
     */
    public static class ValidationResult {
        private boolean valid = true;
        private List<String> errors = new java.util.ArrayList<>();
        
        public void addError(String error) {
            this.valid = false;
            this.errors.add(error);
        }
        
        public boolean isValid() {
            return valid;
        }
        
        public List<String> getErrors() {
            return errors;
        }
        
        public String getErrorMessage() {
            if (errors.isEmpty()) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < errors.size(); i++) {
                if (i > 0) {
                    sb.append("; ");
                }
                sb.append(errors.get(i));
            }
            return sb.toString();
        }
    }
}
